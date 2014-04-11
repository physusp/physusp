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
	        text: 'Energy system contribuitions'
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
}

$(function(){
	
	$("#data").submit(function(){
		
		var input = $(this).serialize();
		var url = $(this).attr("action");
		
		var oxygenConsumptionRest = $('#oxygenConsumptionRest').handsontable('getData');
		input += "&parameters.oxygenConsumptionRest=" + prepareTableToSend(oxygenConsumptionRest);
		
		var oxygenConsumption = $('#oxygenConsumption').handsontable('getData');
		input += "&parameters.oxygenConsumption=" + prepareTableToSend(oxygenConsumption);
		
		var oxygenConsumptionPost = $('#oxygenConsumptionPost').handsontable('getData');
		input += "&parameters.oxygenConsumptionPost=" + prepareTableToSend(oxygenConsumptionPost);
		
		sendFormData(url, input);
		
		return false;
	});
	
	var headers = ["Time (hh:mm:ss)", "Consumption (ml/min)"];
	var handsonConfig = {
	    minSpareRows: 1,
	    colHeaders: headers,
	    contextMenu: true,
	    data: [[null, null]],
	    height: 300
	};
	
	$('#oxygenConsumptionRest').handsontable(handsonConfig);
	$('#oxygenConsumption').handsontable(handsonConfig);
	$('#oxygenConsumptionPost').handsontable(handsonConfig);
	
});
