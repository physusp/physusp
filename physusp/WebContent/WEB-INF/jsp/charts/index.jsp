<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
							<label>Rest lactate concentration (mmol/L)</label> 
							<input type="text" name="parameters.restLactateConcentration" class="form-control" />
							<label>Maximum lactate concentration (mmol/L)</label>
							<input type="text" name="parameters.maxLactateConcentration" class="form-control" />
							<label>Subject mass (kg)</label>
							<input type="text" name="parameters.weight" class="form-control" />
							<label>Rest oxygen consumption file</label>
							<input type="file" name="oxygenConsumptionRest" id="oxygenConsumptionRest"/>
							<label>Exercise oxygen consumption file</label>
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
		<script type="text/javascript" src="<c:url value="/js/lib/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/chart.js"/>"></script>
	</body>
</html>