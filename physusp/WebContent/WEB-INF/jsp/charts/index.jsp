<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<title>PhysUSP</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<form id="data">
						<div class="form-group">
							<label>Concentração de lactato repouso (mmol/L)</label> 
							<input type="text" name="parameters.restLactateConcentration" class="form-control" />
							<label>Concentração de lactato máxima (mmol/L)</label>
							<input type="text" name="parameters.maxLactateConcentration" class="form-control" />
							<label>Massa do indivíduo (kg)</label>
							<input type="text" name="parameters.weight" class="form-control" />
							<label>Arquivo com dados de consumo de oxigênio no repouso</label>
							<input type="file" name="oxygenConsumptionRest" id="oxygenConsumptionRest"/>
							<label>Arquivo com dados de consumo de oxigênio durante o exercício</label>
							<input type="file" name="oxygenConsumption" id="oxygenConsumption" />
							<button type="submit">Send</button>
						</div>
					</form>
				</div>
				<div class="col-md-8">
					<div id="results">
					</div>
				</div>
			</div>
		</div>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script type="text/javascript" src="/Physusp/js/lib/highcharts.js"></script>
		<script type="text/javascript" src="/Physusp/js/chart.js"></script>
	</body>
</html>