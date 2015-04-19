/*
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
*/

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
	var tableId = $(element).data("table");
	var table = $("#" + tableId);
	if(table.is(":hidden"))
		return true;
	return !params || value === "";
}, 'Element must be empty.');

Highcharts.setOptions({
	lang: {
        numericSymbols: null
    }
});

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
		var restOptions = $("#restOptions").detach(), 
			restData = $("#restData").detach();
		$("#aerobicFirstRow").prepend(restOptions);
		$("#aerobicSecondRow").prepend(restData);
	}
	//else if ($("#tab_anaerobicAlactic").hasClass("show")) {
	//	var restOptions = $("#restOptions").detach(), 
	//		restData = $("#restData").detach();
		//$("#anaerobicAlacticFirstRow").prepend(restOptions);
		//$("#anaerobicAlacticSecondRow").prepend(restData);
	//}
	
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

function changeTab(e) {
	var link = $(e.target), tabId = link.parent().attr("id");
	if (tabId == "tabOptions")
		$("#btnPrevious").hide();
	else
		$("#btnPrevious").show();
	if (tabId == "tabResults") {
		$("#btnNext").hide();
		$("#btnNewAnalysis").css("display", "inline-block");
	}
	else {
		$("#btnNext").show();
		$("#btnNewAnalysis").hide();
	}
	
	$(link.attr("href")).find('.handsontable').each(function() {
		fixHandsontableLayout(this);
	});
}

function fixHandsontableLayout(element) {
	/* A renderização só funciona após a segunda chamada... */
	$(element).handsontable('render');
	$(element).handsontable('render');
}

function showChart(data) {
	$('#exportDropdown').show();
	$("#tabResults").show();
	$('#tabResults a').tab('show');
	$("#containerChart").highcharts({
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
        }],
        title: ""
	});
}

function greaterThanZero(value, callback){
	if(value >= 0)
		callback(true);
	else
		callback(false);
}
	
function validateSeries(data) {
	var lastTime = "0";
	for(var i = 0; i < data.length - 10; ++i) {
		if(data[i][0] < lastTime)
			return false;
		lastTime = data[i][0];
	}
	return true;
}

function getHandsontableConfig(errorField) {
	var headers = ["Time <strong>(hh:mm:ss)</strong>", "VO<sub>2</sub> <strong>(ml/min)</strong>"];
	
	return {
	    minSpareRows: 10,
	    colHeaders: headers,
	    contextMenu: true,
	    data: [[null, null]],
	    height: 260,
	    colWidths: [1, 1],
	    stretchH: "all",
		columns: [{
			type: 'text',
			validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9](\.[0-9]+)?)?$/,
			allowInvalid: false
		}, { 	  	
			type: 'numeric',
			format: '0,0.00',
			validator: greaterThanZero,
			allowInvalid: false
		}],
		afterChange: function(changes, source) {
			var data = this.getData();
			if(data.length <= 10)
				errorField.val("Table is empty.");
			else if(!validateSeries(data))
				errorField.val("Table time series is not valid.");
			else
				errorField.val("");
			$('#data').validate().element(errorField);
			this.lastChangeByPaste = source == "paste";
		},
		afterScrollVertically: function() {
			if(this.lastChangeByPaste) {
				this.lastChangeByPaste = false;
				this.selectCell(0, 0, 0, 0, true);
			}
		}
	};	
}

function clearForm() {
	$("#parameters\\.calculateAerobic").prop("checked", "checked");
	$("#parameters\\.calculateAnaerobicLactic").prop("checked", "checked");
	$("#parameters\\.calculateAnaerobicAlactic").prop("checked", "checked");
	$("[name='restOxygenParameters\\.calculateMethod'][value='series']").prop("checked", "checked");
	$("input[type='text']").val("");
}

$(function(){
	
	clearForm();
	
	$("#btnNext").click(setEnergySystems);
	$("#btnPrevious").click(previousEnergySystem);
	
	$("#containerTabs").find("a").on("shown.bs.tab", changeTab);
	
	$("#anaerobicLacticParameters\\.weight").warning(function(field) {
		return ($(field).val() == "" || $(field).val() > 40);
	}, "Weight value is too low?");
	
	$('#oxygenConsumptionRest').handsontable(getHandsontableConfig($('#oxygenConsumptionRestError')));
	$('#oxygenConsumptionDuringExercise').handsontable(getHandsontableConfig($('#oxygenConsumptionDuringExerciseError')));
	$('#oxygenConsumptionPostExercise').handsontable(getHandsontableConfig($('#oxygenConsumptionPostExerciseError')));
	
	$('#aerobic input:radio').change(function() {
		$("#aerobicRestTable").toggle($(this).val() == "series");
		$("#aerobicRestAvg").toggle($(this).val() == "fixed");
		fixHandsontableLayout("#oxygenConsumptionRest");
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
	
	$("#btnOxygenConsumptionRestClear,#btnOxygenConsumptionDuringExerciseClear,#btnOxygenConsumptionPostExerciseClear").click(function() {
		var tableId = $(this).data("table");
		$('#' + tableId).handsontable('clear');
		return false;
	});
});
