$(function(){
	
	$("#data").submit(function(){
		var input = $(this).serialize();
		
		$.ajax({
			url: "/Physusp/charts/calculate",
			type: "POST",
			data: input
		})
		.done(function(result){
			var data = []; 
			$.each(result.consumption, function(key, value) { data.push([key, value]);});
			showChart(data);
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
});
