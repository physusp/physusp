function prepareTableToSend(tableRows){
	var toSend = "";
	for(var i = 0; i < tableRows.length - 1; i++)
		toSend += tableRows[i][0] + "," + tableRows[i][1] + "\n";
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
		$.each(result.consumption, function(key, value) { data.push([key, value]);});
		showChart(data);
	});
}

function showChart(data) {
	$("#results").highcharts({
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
        series: [{
            type: 'pie',
            name: 'Contribution',
            data: data
        }]
	});
	$('#expimage').show();
	$('#exppdf').show();
}

function exportToImage() {
	var chart = $('#results').highcharts();
	chart.exportChart({
		filename: 'energy systems contributions ' + new Date()
	});
}

function exportToPdf() {
	var chart = $('#results').highcharts();
	chart.exportChart({
		type: 'application/pdf',
		filename: 'energy systems contributions ' + new Date()
	});
}

$(function(){
	$("#data").submit(function(){
		var input = $(this).serialize();
		var url = $(this).attr("action");
		
		var oxygenConsumptionRest = $('#oxygenConsumptionRest').handsontable('getData');
		input += "&parameters.oxygenConsumptionRest=" + prepareTableToSend(oxygenConsumptionRest);
		
		var oxygenConsumptionDuringExercise = $('#oxygenConsumptionDuringExercise').handsontable('getData');
		input += "&parameters.oxygenConsumptionDuringExercise=" + prepareTableToSend(oxygenConsumptionDuringExercise);
		
		var oxygenConsumptionPostExercise = $('#oxygenConsumptionPostExercise').handsontable('getData');
		input += "&parameters.oxygenConsumptionPostExercise=" + prepareTableToSend(oxygenConsumptionPostExercise);
		
		sendFormData(url, input);
		
		return false;
	});
	
	var headers = ["Time (hh:mm:ss)", "Consumption (ml/min)"];
	
	$('#oxygenConsumptionRest').handsontable({
	    minSpareRows: 1,
	    colHeaders: headers,
	    contextMenu: true,
	    data: [[null, null]],
	    height: 300
	});
	$('#oxygenConsumptionDuringExercise').handsontable({
	    minSpareRows: 1,
	    colHeaders: headers,
	    contextMenu: true,
	    data: [[null, null]],
	    height: 300
	});
	$('#oxygenConsumptionPostExercise').handsontable({
	    minSpareRows: 1,
	    colHeaders: headers,
	    contextMenu: true,
	    data: [[null, null]],
	    height: 300
	});
	$('#expimage').hide();
	$('#exppdf').hide();
});
