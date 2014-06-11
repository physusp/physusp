function calculateResults() {
	var form = $("#data");
	if(!form.valid()) return;
	
	var input = form.serialize();
	var url = form.attr("action");
	
	var tables = [$('#oxygenConsumptionRest'), $('#oxygenConsumptionDuringExercise'), $('#oxygenConsumptionPostExercise')];
	
	for(var i = 0; i < tables.length; i++) {
		var oxygenConsumption = new OxygenConsumptionTable(tables[i]);
		input += "&" + oxygenConsumption.getDataAsString();
	}

	$.ajax({
		url: url,
		type: "POST",
		data: input,
		success: onCalculateSuccess,
		error: onCalculateError
	});
}

function onCalculateSuccess(result) {
	var data = [];
	if(result.consumption.aerobic != undefined){
		data.push(["Aerobic", parseFloat(result.consumption.aerobic.Kcal)]);
		var aerobicRow = new ResultRow($("#aerobicRow"), result.consumption.aerobic);
		aerobicRow.showResult();
	}
	if(result.consumption.anaerobicLactic != undefined){
		data.push(["Anaerobic Lactic", parseFloat(result.consumption.anaerobicLactic.Kcal)]);
		var anaerobicLacticRow = new ResultRow($("#anaerobicLacticRow"), result.consumption.anaerobicLactic);
		anaerobicLacticRow.showResult();
	}
	if(result.consumption.anaerobicAlactic != undefined){
		data.push(["Anaerobic Alactic", parseFloat(result.consumption.anaerobicAlactic.Kcal)]);
		var anaerobicAlacticRow = new ResultRow($("#anaerobicAlacticRow"), result.consumption.anaerobicAlactic);
		anaerobicAlacticRow.showResult();
	}
	showChart(data);
	showAdvancedResults(result);
}

function onCalculateError(data) {
	$("#modal-error").find("#error-content").text("");
	if(data.responseJSON != undefined)
		$("#modal-error").find("#error-content").text(data.responseJSON.exceptionInfo.message);
	$("#modal-error").modal("show");
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
				empty: function() { return $("#oxygenConsumptionRestError").val(); }
			},
			"oxygenConsumptionDuringExerciseError": {
				empty: function() { return $("#oxygenConsumptionDuringExerciseError").val(); }
			},
			"oxygenConsumptionPostExerciseError": {
				empty: function() { return $("#oxygenConsumptionPostExerciseError").val(); }
			} 
		}
	});
});