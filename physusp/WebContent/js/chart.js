$(function(){
	
	$("#data").submit(function(){
		var input = $(this).serializeArray();
		
		var inputJson = {}; 
		$.each(input, function() { inputJson[this.name] = this.value; });
		
		$.ajax({
			url: "/Physusp/charts/calculate",
			type: "POST",
			data: JSON.stringify({"consumption": inputJson}),
			dataType: "json",
			contentType: "application/json"
		})
		.done(function(result){
			showChart(result);
		});
		
		return false;
	});
	
	function showChart(data)
	{
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
		    	pointFormat: '{series.name}: <b>{point.y:.1f}</b> <br/><b>{point.percentage:.1f}%</b>'
		    },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
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
});
