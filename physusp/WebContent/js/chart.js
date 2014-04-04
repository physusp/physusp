$(function(){
	
	$("#data").submit(function(){
		
		var input = $(this).serialize();
		
		var restFileLoaded = false;
		var fileLoaded = false;
		var restFile = document.getElementById("oxygenConsumptionRest").files[0];
		var restFileReader = new FileReader();
		restFileReader.readAsText(restFile, 'UTF-8');
		restFileReader.onload = function(evt) {
			input += "&" + $.param({"parameters.oxygenConsumptionRest": evt.target.result});
			if(fileLoaded)
				sendDataForm();
			restFileLoaded = true;
		};
		
		var file = document.getElementById("oxygenConsumption").files[0];
		var fileReader = new FileReader();
		fileReader.readAsText(file, 'UTF-8');
		fileReader.onload = function(evt) {
			input += "&" + $.param({"parameters.oxygenConsumption": evt.target.result});
			if(restFileLoaded)
				sendDataForm();
			fileLoaded = true;
		};
		
		
		function sendDataForm() {
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
		}
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
