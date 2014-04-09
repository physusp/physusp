var FilesLoader = function () {
	this.files = [];
	this.output = "";
	
	this.addFile = function(file, parameterName) {
		this.files.push({file: file, loaded: false, parameterName: parameterName});
	};
	
	this.loadFiles = function (callback) {
		var $this = this;
		this.files.forEach(function(item){
			var fileReader = new FileReader();
			fileReader.readAsText(item.file);
			fileReader.onload = function(evt){
				var object = {};
				object[item.parameterName] = evt.target.result;
				$this.output += "&" + $.param(object);
				item.loaded = true;
				if($this.hasLoadedAllFiles())
					callback();
			};
		});
	};
	
	this.hasLoadedAllFiles = function (){
		var loadedAllFiles = true;
		this.files.forEach(function(item){
			if(!item.loaded) {
				loadedAllFiles = false;
			}
		});
		return loadedAllFiles;
	};
	
};

$(function(){
	
	$("#data").submit(function(){
		
		var input = $(this).serialize();
		var url = $(this).attr("action");
		
		var filesLoader = new FilesLoader();
		
		filesLoader.addFile(document.getElementById("oxygenConsumptionRest").files[0], "parameters.oxygenConsumptionRest");
		filesLoader.addFile(document.getElementById("oxygenConsumption").files[0], "parameters.oxygenConsumption");
		
		filesLoader.loadFiles(function() {
			sendDataForm(url, input + filesLoader.output);
		});
		
		return false;
	});
	
	function sendDataForm(url, formData) {
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
